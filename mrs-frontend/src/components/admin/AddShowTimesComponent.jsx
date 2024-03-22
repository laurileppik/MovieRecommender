import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { setShowTime } from '../../services/ShowTimeService';

const AddShowTimesComponent = () => {
    const [showtime, setShowTimeState] = useState({
        name: '',
        startTime: '',
        screenId: '',
    });

    const navigate = useNavigate();
    
    //Meetod juhuks kui väärtus lünga sees muutub
    const handleChange = (e) => {
        const { name, value } = e.target;
        setShowTimeState({
            ...showtime,
            [name]: value,
        });
    };

    //REST API vaatamisaja lisamiseks
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(showtime);
            await setShowTime(showtime);
            alert('Showtime added successfully');
            navigate('/');
        } catch (error) {
            alert(error.message);
        }
    };

    return (
        <div>
            <h2>Lisa seanss</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nimi:</label>
                    <input
                        type="text"
                        name="name"
                        value={showtime.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Algusaeg:</label>
                    <input
                        type="datetime-local"
                        name="startTime"
                        value={showtime.startTime}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Saal:</label>
                    <input
                        type="number"
                        name="screenId"
                        value={showtime.screenId}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Lisa seanss</button>
            </form>
        </div>
    );
};

export default AddShowTimesComponent;
