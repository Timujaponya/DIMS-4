import { useEffect, useState } from 'react';
import axios from 'axios';

function AccountList({ refreshTrigger }) {
    const [accounts, setAccounts] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/account/all')
            .then(res => {
                setAccounts(res.data);
            })
            .catch(err => {
                console.error("Hesap verisi alınamadı:", err);
                setError("Veri alınamadı.");
            });
    }, [refreshTrigger]); // 🔁 Bu değer değişince veri yeniden çekilir

    if (error) return <p style={{ color: 'red' }}>{error}</p>;
    if (accounts.length === 0) return <p>Yükleniyor...</p>;

    return (
        <ul>
            {accounts.map(account => (
                <li key={account.id}>
                    <strong>#{account.id}</strong> – {account.branch} – {account.balance}₺
                </li>
            ))}
        </ul>
    );
}

export default AccountList;
