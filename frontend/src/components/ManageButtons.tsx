import { Button, Container, Typography } from '@mui/material';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { deleteProperty } from '../api/properties.api';

type Props = {
  id: string;
};

export default function ManageButtons(props: Props) {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const propertyId = props.id;

  const handleNavigate = (path: string) => {
    navigate(path);
  };

  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: deleteProperty,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['properties'] });
      navigate('/properties');
    },
  });

  return (
    <Container sx={{ backgroundColor: '#30343F', py: 5 }} maxWidth="xl">
      <Button
        variant="contained"
        color="primary"
        onClick={() => handleNavigate(`/properties/manage?propertyId=${propertyId}`)}
      >
        Edit
      </Button>
      <Button variant="contained" color="secondary" sx={{ marginLeft: 2 }} onClick={() => mutate({ propertyId })}>
        Delete
      </Button>
      {isPending && (
        <div>
          <i>Deleting...</i>
        </div>
      )}
      {isError && (
        <Typography className="error" variant="h4">
          Error occurred: {error.message}
        </Typography>
      )}
    </Container>
  );
}
