import React, { useState } from 'react'; 
import axios from 'axios'; 
import { useNavigate } from 'react-router-dom';
import { MDBContainer, MDBInput } from 'mdb-react-ui-kit'; 
  
function SignupComponent() { 
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [username, setUsername] = useState(''); 
    const [password, setPassword] = useState(''); 
    const [confirmPassword, setConfirmPassword] = useState(''); 
    const [birthDate, setbirthDate] = useState(''); 
    const [error, setError] = useState('');
    const history = useNavigate();
  
    const handleSignup = async () => { 
        try { 
            if (!firstName || !lastName ||!username || !password || !confirmPassword || !birthDate) { 
                setError('Please fill in all fields.'); 
                return; 
            } 
  
            if (password !== confirmPassword) { 
                throw new Error("Passwords do not match"); 
            } 
  
            const response = await axios.post('http://localhost:8080/auth/signup', { 
                firstName,
                lastName, 
                userName: username, 
                password, 
                role: 'USER', 
                birthDate: birthDate 
            }); 
            console.log(response.data); 
            history('/login'); 
        } catch (error) { 
            console.error('Signup failed:', error.response ? error.response.data : error.message); 
            setError(error.response ? error.response.data : error.message); 
        } 
    }; 
  
    return ( 
        <div className="d-flex justify-content-center align-items-center vh-100"> 
            <div className="border rounded-lg p-4" style={{width: '600px', height: 'auto'}}> 
                <MDBContainer className="p-3"> 
                    <h2 className="mb-4 text-center">Sign Up Page</h2>  
                    {error && <p className="text-danger">{error}</p>} 
                    <MDBInput wrapperClass='mb-3' id='firstName' placeholder={"First Name"} value={firstName} type='text'
                                onChange={(e) => setFirstName(e.target.value)}/>
                    <MDBInput wrapperClass='mb-3' id='lastName' placeholder={"Last Name"} value={lastName} type='text'
                                onChange={(e) => setLastName(e.target.value)}/>
                    <MDBInput wrapperClass='mb-3' placeholder='Username' id='userName' value={username} type='userName'
                              onChange={(e) => setUsername(e.target.value)}/> 
                    <MDBInput wrapperClass='mb-3' placeholder='Password' id='password' type='password' value={password} 
                              onChange={(e) => setPassword(e.target.value)}/> 
                    <MDBInput wrapperClass='mb-3' placeholder='Confirm Password' id='confirmPassword' type='password'
                              value={confirmPassword} 
                              onChange={(e) => setConfirmPassword(e.target.value)}/> 
  
  
                    <MDBInput wrapperClass='mb-2' placeholder='Birthdate' id='birthdate' value={birthDate} 
                              type='date'
                              onChange={(e) => setbirthDate(e.target.value)}/> 
                    <button className="mb-4 d-block mx-auto fixed-action-btn btn-primary"
                            style={{height: '40px', width: '100%'}} 
                            onClick={handleSignup}>Sign Up 
                    </button> 
  
                    <div className="text-center"> 
                        <p>Already Registered? <a href="/login">Login</a></p> 
                    </div> 
  
                </MDBContainer> 
            </div> 
        </div> 
    ); 
} 
  
export default SignupComponent; 