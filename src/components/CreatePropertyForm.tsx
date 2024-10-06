import { Container, Typography, TextField, Button } from '@mui/material';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProperty } from '../api/properties.api';

export default function CreatePropertyForm() {
  const [address, setAddress] = useState<string>('');
  const [salePrice, setSalePrice] = useState<number>(0);
  const [numberOfRooms, setNumberOfRooms] = useState<number>(0);
  const [propertyType, setPropertyType] = useState<string>('');
  const [area, setArea] = useState<number>(0);
  const [ownerId, setOwnerId] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [createdSuccesfully, setCreatedSuccesfully] = useState<boolean>(false);
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const { mutate, data, isPending, isError, error } = useMutation({
    mutationFn: createProperty,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['properties'] });
      setCreatedSuccesfully(true);
    },
  });

  useEffect(() => {
    if (createdSuccesfully && data) {
      navigate(`/properties/${data.id}`);
    }
  }, [createdSuccesfully]);

  return (
    <>
      <Typography textAlign="center">Create property</Typography>
      <Container sx={{ marginTop: 5 }}>
        <TextField
          id="outlined-primary"
          color="primary"
          focused
          label="Address"
          defaultValue={address}
          onChange={(e) => setAddress(e.target.value)}
          InputProps={{
            style: { color: 'white' },
          }}
          sx={{ width: '100%' }}
        />
      </Container>
      <Container sx={{ marginTop: 2 }}>
        <TextField
          id="outlined-primary"
          color="primary"
          focused
          label="Sale price"
          defaultValue={salePrice}
          onChange={(e) => setSalePrice(Number(e.target.value))}
          InputProps={{
            style: { color: 'white' },
          }}
          sx={{ width: '100%' }}
        />
      </Container>
      <Container sx={{ marginTop: 2 }}>
        <TextField
          id="outlined-primary"
          color="primary"
          focused
          label="Number of rooms"
          defaultValue={numberOfRooms}
          onChange={(e) => setNumberOfRooms(Number(e.target.value))}
          InputProps={{
            style: { color: 'white' },
          }}
          sx={{ width: '100%' }}
        />
      </Container>
      <Container sx={{ marginTop: 2 }}>
        <TextField
          id="outlined-primary"
          color="primary"
          focused
          label="Property type"
          defaultValue={propertyType}
          onChange={(e) => setPropertyType(e.target.value)}
          InputProps={{
            style: { color: 'white' },
          }}
          sx={{ width: '100%' }}
        />
      </Container>
      <Container sx={{ marginTop: 2 }}>
        <TextField
          id="outlined-primary"
          color="primary"
          focused
          label="Area"
          defaultValue={area}
          onChange={(e) => setArea(Number(e.target.value))}
          InputProps={{
            style: { color: 'white' },
          }}
          sx={{ width: '100%' }}
        />
      </Container>
      <Container sx={{ marginTop: 2 }}>
        <TextField
          id="outlined-primary"
          color="primary"
          focused
          label="Owner"
          defaultValue={ownerId}
          onChange={(e) => setOwnerId(e.target.value)}
          InputProps={{
            style: { color: 'white' },
          }}
          sx={{ width: '100%' }}
        />
      </Container>
      <Container sx={{ marginTop: 2 }}>
        <TextField
          id="outlined-primary"
          color="primary"
          focused
          label="Description"
          defaultValue={description}
          onChange={(e) => setDescription(e.target.value)}
          InputProps={{
            style: { color: 'white' },
          }}
          sx={{ width: '100%' }}
        />
      </Container>
      <Container sx={{ marginTop: 4 }}>
        <Button
          variant="contained"
          onClick={() => mutate({ address, salePrice, numberOfRooms, propertyType, area, ownerId, description })}
        >
          Create property
        </Button>
      </Container>
      {isPending && (
        <div>
          <i>Adding...</i>
        </div>
      )}
      {isError && <div className="error">Error occurred: {error.message}</div>}
    </>
  );
}
