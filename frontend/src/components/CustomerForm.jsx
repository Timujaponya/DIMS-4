import { useState } from 'react';
import axios from 'axios';

function CustomerForm({ onSuccess }) {
    const [name, setName] = useState('');
    const [city, setCity] = useState('');
    const [address, setAddress] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        axios.post('http://localhost:8080/customer/add', {
            name,
            city,
            address
        })
            .then(() => {
                setMessage('✅ Müşteri başarıyla eklendi.');
                setName('');
                setCity('');
                setAddress('');
                if (onSuccess) onSuccess(); // Listeyi yenileme sinyali gönder
            })
            .catch(() => {
                setMessage('❌ Müşteri eklenemedi.');
            });
    };

    return (
        <form
            onSubmit={handleSubmit}
            style={{
                display: 'flex',
                flexDirection: 'column',
                gap: '0.5rem',
                maxWidth: '300px',
                marginBottom: '2rem'
            }}
        >
            <input
                type="text"
                placeholder="Ad Soyad"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />
            <input
                type="text"
                placeholder="Şehir"
                value={city}
                onChange={(e) => setCity(e.target.value)}
                required
            />
            <input
                type="text"
                placeholder="Adres"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                required
            />
            <button type="submit">Müşteri Ekle</button>
            {message && <p>{message}</p>}
        </form>
    );
}

export default CustomerForm;
