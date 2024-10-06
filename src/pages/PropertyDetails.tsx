import { useParams } from 'react-router-dom';
import { CircularProgress, Container, Typography } from '@mui/material';
import ManageButtons from '../components/ManageButtons';
import useProperty from '../hooks/useProperty';

export default function PropertyDetails() {
  const { propertyId } = useParams() as { propertyId: string };
  const { data, isLoading, isError } = useProperty(propertyId);

  if (isError) {
    return (
      <Typography variant="h5" marginTop={5} textAlign="center">
        Error fetching properties
      </Typography>
    );
  }

  if (isLoading) {
    return (
      <Container sx={{ margin: 'auto', marginTop: 5 }}>
        <CircularProgress />;
      </Container>
    );
  }

  return (
    <Container sx={{ backgroundColor: '#30343F', py: 5 }} maxWidth="xl">
      <Typography variant="h2" textAlign="center" marginBottom={5}>
        Property no. {propertyId}
      </Typography>
      <hr />
      <Typography variant="h5" textAlign="center" marginTop={5}>
        Property type: {data?.propertyType}
      </Typography>
      <Typography variant="h5" textAlign="center" marginTop={1}>
        Address: {data?.address}
      </Typography>
      <Typography variant="h5" textAlign="center" marginTop={1}>
        Area: {data?.area} m^2
      </Typography>
      <Typography variant="h5" textAlign="center" marginTop={1}>
        Number of rooms: {data?.numberOfRooms}
      </Typography>
      <Typography variant="h5" textAlign="center" marginTop={1}>
        Sale price: {data?.salePrice}
      </Typography>
      <Typography variant="h5" textAlign="center" marginTop={1}>
        Owner: {data?.ownerId}
      </Typography>
      <Typography variant="h5" textAlign="center" marginTop={1} marginBottom={5}>
        Description: {data?.description}
      </Typography>
      <hr />
      <ManageButtons id={propertyId} />
    </Container>
  );
}
