import { useQuery } from '@tanstack/react-query';
import { fetchProperty } from '../api/properties.api';

export default function useProperty(propertyId: string) {
  const { data, isError, isLoading } = useQuery({
    queryKey: ['property', { id: propertyId }],
    queryFn: () => fetchProperty(propertyId),
  });

  return { data, isError, isLoading };
}
