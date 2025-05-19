import { useState } from 'react';
import AccountList from './components/AccountList';
import CustomerList from './components/CustomerList';
import LoanList from './components/LoanList';
import BankingForm from './components/BankingForm';
import AccountForm from './components/AccountForm';
import CustomerForm from './components/CustomerForm';

function App() {
        const [refreshCustomers, setRefreshCustomers] = useState(false);
        const [refreshAccounts, setRefreshAccounts] = useState(false);

        const handleCustomerAdded = () => {
                setRefreshCustomers(prev => !prev); // müşteri listesi tetiklenir
        };

        const handleAccountAdded = () => {
                setRefreshAccounts(prev => !prev); // hesap listesi tetiklenir
        };

        return (
            <div style={{ padding: '2rem', fontFamily: 'Arial' }}>
                    <h1>🏦 Banka Paneli</h1>

                    <h2>➕ Yeni Hesap Ekle</h2>
                    <AccountForm onSuccess={handleAccountAdded} />

                    <h2>📋 Hesaplar</h2>
                    <AccountList refreshTrigger={refreshAccounts} />

                    <h2>➕ Yeni Müşteri Ekle</h2>
                    <CustomerForm onSuccess={handleCustomerAdded} />

                    <h2>👤 Müşteriler</h2>
                    <CustomerList refreshTrigger={refreshCustomers} />

                    <h2>💸 Krediler</h2>
                    <LoanList />

                    <h2>💰 Para Transferi</h2>
                    <BankingForm />
            </div>
        );
}

export default App;
