import { useEffect, useState } from 'react';
import axios from 'axios';

function LoanList() {
    const [loans, setLoans] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/loan/all')
            .then(res => {
                setLoans(res.data);
            })
            .catch(err => {
                console.error("Kredi verisi alınamadı:", err);
                setError("Veri alınamadı.");
            });
    }, []);

    if (error) return <p style={{ color: 'red' }}>{error}</p>;
    if (loans.length === 0) return <p>Yükleniyor...</p>;

    return (
        <ul>
            {loans.map(loan => (
                <li key={loan.id}>
                    <strong>#{loan.id}</strong> – {loan.amount}₺ – {loan.loanDate}
                </li>
            ))}
        </ul>
    );
}

export default LoanList;
