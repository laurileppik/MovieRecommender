/** Enamus koodi selles klassis on võetud siit: https://www.geeksforgeeks.org/spring-security-login-page-with-react/
 * Most of the code in this class is taken from here: https://www.geeksforgeeks.org/spring-security-login-page-with-react/
 * **/

import React, { useState } from 'react'; 
import axios from 'axios'; 
import { useNavigate } from 'react-router-dom'; 
import { MDBContainer, MDBInput, MDBBtn, } from 'mdb-react-ui-kit'; 
  
function LoginPage() { 
    const [userName, setUsername] = useState(''); 
    const [password, setPassword] = useState(''); 
    const [error, setError] = useState(''); 
    const history = useNavigate(); 
  
    const handleLogin = async () => { 
        try { 
            if (!userName || !password) { 
                setError('Palun sisesta nii kasutajanimi kui ka parool.'); 
                return; 
            } 
  
            const response = await axios.post('http://localhost:8080/auth/signin', { userName: userName, password }); 
            console.log('Login successful:', response.data);
            const userId= response.data.id;
            const jwtToken = response.data.jwt;
            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('customerId', userId);
            history('/'); 
        } catch (error) { 
            console.error('Sisselogimine ebaõnnestus: ', error.response ? error.response.data : error.message); 
            setError('Vale kasutajanimi või parool.'); 
        } 
    }; 
  
    return ( 
        <div className="d-flex justify-content-center align-items-center vh-100"> 
            <div className="border rounded-lg p-4" style={{ width: '500px', height: 'auto' }}> 
                <MDBContainer className="p-3"> 
                    <h2 className="mb-4 text-center">Sisse logimine</h2> 
                    <MDBInput wrapperClass='mb-4' placeholder='Kasutajanimi' id='email' value={userName} type='email' onChange={(e) => setUsername(e.target.value)} /> 
                    <MDBInput wrapperClass='mb-4' placeholder='Parool' id='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} /> 
                    {error && <p className="text-danger">{error}</p>} {} 
                    <button className="mb-4 d-block btn-primary" style={{ height:'50px',width: '100%' }} onClick={handleLogin}>Logi sisse</button> 
                    <div className="text-center"> 
                        <p>Ei ole kasutaja? <a href="/signup" >Registreeri</a></p> 
                    </div> 
                </MDBContainer> 
            </div> 
        </div> 
    ); 
} 
  
export default LoginPage; 