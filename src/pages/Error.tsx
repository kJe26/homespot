import { Typography } from '@mui/material';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Error() {
  const navigate = useNavigate();
  useEffect(() => {
    const timeout = setTimeout(() => {
      navigate('/');
    }, 5000);
    return () => clearTimeout(timeout);
  }, [navigate]);

  return (
    <>
      <Typography variant="h2" textAlign="center" marginTop={10}>
        404 Not Found
      </Typography>
      <Typography variant="h6" textAlign="center" marginTop={2}>
        Redirecting to home page...
      </Typography>
    </>
  );
}
