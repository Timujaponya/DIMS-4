import React from 'react';

function DownloadPdfButton() {
    const downloadPdf = () => {
        fetch('http://localhost:8080/customer/report')
            .then(response => {
                if (!response.ok) {
                    throw new Error("PDF indirilemedi.");
                }
                return response.blob();
            })
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'customer_report.pdf';
                a.click();
                window.URL.revokeObjectURL(url); // Temizlik
            })
            .catch(error => {
                console.error('İndirme hatası:', error);
                alert("PDF indirilemedi.");
            });
    };

    return (
        <button onClick={downloadPdf} style={{ margin: "10px", padding: "8px 16px" }}>
            📄 PDF Raporu İndir
        </button>
    );
}

export default DownloadPdfButton;
