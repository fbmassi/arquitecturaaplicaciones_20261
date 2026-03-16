import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';

const errorRate = new Rate('error_rate');

export const options = {
    stages: [
        { duration: '5s',  target: 50 },  // Rampa de subida a 50 VUs
        { duration: '10s', target: 50 },  // Sostenido en 50 VUs
        { duration: '5s',  target: 0  },  // Rampa de bajada
    ],
    thresholds: {
        // SLO objetivo de la clase (fallará intencionalmente por Thread.sleep(200))
        'http_req_duration': ['p(95)<50'],
        // Objetivo realista considerando el sleep de 200ms
        'http_req_duration': ['p(95)<300'],
        // Disponibilidad
        'error_rate': ['rate<0.01'],
    },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

export default function () {
    const res = http.get(`${BASE_URL}/api/productos`);

    const passed = check(res, {
        'status es 200':         (r) => r.status === 200,
        'respuesta es JSON':     (r) => r.headers['Content-Type']?.includes('application/json'),
        'contiene 3 productos':  (r) => JSON.parse(r.body).length === 3,
        'latencia < 50ms (SLO)': (r) => r.timings.duration < 50,
    });

    errorRate.add(!passed);

    sleep(0.5);
}
