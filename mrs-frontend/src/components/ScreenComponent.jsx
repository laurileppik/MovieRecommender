import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getScreen} from '../services/ScreenService'

const ScreenComponent = () => {
    const { screenId } = useParams();
    const [screen, setScreen] = useState(null);

    useEffect(() => {
        const fetchScreen = async () => {
          try {
            const response = await getScreen(screenId);
            setScreen(response.data);
          } catch (error) {
            console.error(error);
          }
        };
    
        fetchScreen();
      }, [screenId]);
      return (
        <div>
            {screen && (
                <div>
                    <p>{screen.id}</p>
                </div>
            )}
        </div>
      );
}

export default ScreenComponent;