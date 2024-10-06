import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Container, TextField, Typography } from '@mui/material';
import { updateProperty } from '../api/properties.api';

type Props = {
  propertyId: string;
};

export default function UpdatePropertyForm(props: Props) {
  const [address, setAddress] = useState<string>('');
  const [salePrice, setSalePrice] = useState<number>(0);
  const [numberOfRooms, setNumberOfRooms] = useState<number>(0);
  const [propertyType, setPropertyType] = useState<string>('');
  const [area, setArea] = useState<number>(0);
  const [ownerId, setOwnerId] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [updatedSuccesfully, setUpdatedSuccesfully] = useState<boolean>(false);
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const id = props.propertyId;

  const { mutate, data, isPending, isError, error } = useMutation({
    mutationFn: updateProperty,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['property', { id }] });
      setUpdatedSuccesfully(true);
    },
  });

  useEffect(() => {
    if (updatedSuccesfully && data) {
      navigate(`/properties/${data.id}`);
    }
  }, [updatedSuccesfully]);

  return (
    <>
      <Typography textAlign="center">Update property</Typography>
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
          onClick={() => mutate({ id, address, salePrice, numberOfRooms, propertyType, area, ownerId, description })}
        >
          Update it
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
