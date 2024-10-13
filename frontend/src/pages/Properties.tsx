import { CircularProgress, Container, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import { Property } from '../api/properties.api';
import useProperties from '../hooks/useProperties';

export default function Properties() {
  const { data, isLoading, isError } = useProperties();

  if (isError) {
    return (
      <Typography variant="h5" marginTop={5} textAlign="center">
        Error fetching properties
      </Typography>
    );
  }

  if (isLoading) {
    return <CircularProgress />;
  }

  return (
    <Container sx={{ backgroundColor: '#30343F', py: 5 }} maxWidth="xl">
      <Typography variant="h2" textAlign="center">
        Properties
      </Typography>
      <ul>
        {data?.map((property: Property) => (
          <li key={property.id}>
            <Typography variant="h5" marginTop={5}>
              Property type: {property?.propertyType}
            </Typography>
            <Typography variant="h5" marginTop={1}>
              Address: {property?.address}
            </Typography>
            <Typography variant="h5" marginTop={1}>
              Area: {property?.area} m^2
            </Typography>
            <Typography variant="h5" marginTop={1}>
              Number of rooms: {property?.numberOfRooms}
            </Typography>
            <Typography variant="h5" marginTop={1}>
              Sale price: {property?.salePrice}
            </Typography>
            <Typography variant="h5" marginTop={1}>
              Owner: {property?.ownerId}
            </Typography>
            <Link to={`/properties/${property.id}`}>View Details</Link>
          </li>
        ))}
      </ul>
    </Container>
  );
}
