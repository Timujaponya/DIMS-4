import { useState } from 'react';
import axios from 'axios';

function AccountForm({ onSuccess }) {
    const [branch, setBranch] = useState('');
    const [balance, setBalance] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        axios.post('http://localhost:8080/account/add', {
            branch,
            balance
        })
            .then(() => {
                setMessage('✅ Hesap başarıyla eklendi.');
                setBranch('');
                setBalance('');
                if (onSuccess) onSuccess(); // listeyi tetikle
            })
            .catch(() => {
                setMessage('❌ Hesap eklenemedi.');
            });
    };

    return (
        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem', maxWidth: '300px', marginBottom: '2rem' }}>
            <input
                type="text"
                placeholder="Şube adı"
                value={branch}
                onChange={(e) => setBranch(e.target.value)}
                required
            />
            <input
                type="number"
                placeholder="Bakiye (₺)"
                value={balance}
                onChange={(e) => setBalance(e.target.value)}
                required
            />
            <button type="submit">Hesap Ekle</button>
            {message && <p>{message}</p>}
        </form>
    );
}

export default AccountForm;
