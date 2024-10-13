import { Container } from '@mui/material';
import { useSearchParams } from 'react-router-dom';
import CreatePropertyForm from '../components/CreatePropertyForm';
import UpdatePropertyForm from '../components/UpdatePropertyForm';

export default function ManageProperties() {
  const [searchParams] = useSearchParams();
  return (
    <Container sx={{ backgroundColor: '#30343F', py: 5 }} maxWidth="xl">
      {searchParams.get('propertyId') == null ? (
        <CreatePropertyForm />
      ) : (
        <UpdatePropertyForm propertyId={searchParams.get('propertyId') as string} />
      )}
    </Container>
  );
}
