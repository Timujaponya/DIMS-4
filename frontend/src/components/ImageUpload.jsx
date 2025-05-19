import { useState } from 'react';
import axios from 'axios';

function ImageUpload({ customerId }) {
    const [file, setFile] = useState(null);
    const [message, setMessage] = useState("");

    const handleUpload = () => {
        const formData = new FormData();
        formData.append("file", file);

        axios.post(`http://localhost:8080/images/upload/${customerId}`, formData)
            .then(res => {
                setMessage("✅ Resim yüklendi: " + res.data);
            })
            .catch(() => {
                setMessage("❌ Yükleme başarısız.");
            });
    };

    return (
        <div>
            <input type="file" onChange={(e) => setFile(e.target.files[0])} />
            <button onClick={handleUpload}>Resim Yükle</button>
            {message && <p>{message}</p>}
        </div>
    );
}

export default ImageUpload;
