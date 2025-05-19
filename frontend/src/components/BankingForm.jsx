import { useState } from 'react';
import axios from 'axios';

function BankingForm() {
    const [fromAccountId, setFromAccountId] = useState('');
    const [toAccountId, setToAccountId] = useState('');
    const [amount, setAmount] = useState('');
    const [message, setMessage] = useState('');

    const handleTransfer = (e) => {
        e.preventDefault();

        axios.post('http://localhost:8080/banking/transfer', {
            fromAccountId,
            toAccountId,
            amount
        })
            .then(() => {
                setMessage("✅ Transfer başarılı.");
            })
            .catch((err) => {
                console.error(err);
                setMessage("❌ Transfer başarısız.");
            });
    };

    return (
        <form onSubmit={handleTransfer} style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem', maxWidth: '300px' }}>
            <input
                type="number"
                placeholder="Gönderen hesap ID"
                value={fromAccountId}
                onChange={(e) => setFromAccountId(e.target.value)}
                required
            />
            <input
                type="number"
                placeholder="Alıcı hesap ID"
                value={toAccountId}
                onChange={(e) => setToAccountId(e.target.value)}
                required
            />
            <input
                type="number"
                placeholder="Miktar"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                required
            />
            <button type="submit">Gönder</button>
            {message && <p>{message}</p>}
        </form>
    );
}

export default BankingForm;
