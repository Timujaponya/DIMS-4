import { useEffect, useState } from 'react';
import axios from 'axios';
import DownloadPdfButton from './DownloadPdfButton';

function CustomerList({ refreshTrigger }) {
    const [customers, setCustomers] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/customer/all')
            .then(res => {
                setCustomers(res.data);
            })
            .catch(err => {
                console.error("Müşteri verisi alınamadı:", err);
                setError("Veri alınamadı.");
            });
    }, [refreshTrigger]); // 🟢 refresh tetikleyiciye bağlı

    if (error) return <p style={{ color: 'red' }}>{error}</p>;
    if (customers.length === 0) return <p>Yükleniyor...</p>;

    return (
        <div>
            <h2>Müşteri Listesi</h2>

            {/* PDF butonu */}
            <DownloadPdfButton />

            <ul>
                {customers.map(c => (
                    <li key={c.id}>
                        <strong>#{c.id}</strong> – {c.name}, {c.city} – {c.address}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default CustomerList;
