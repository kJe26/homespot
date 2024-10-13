import { useQuery } from '@tanstack/react-query';
import { fetchAllProperties } from '../api/properties.api';

export default function useProperties() {
  const { data, isLoading, isError } = useQuery({
    queryKey: ['properties'],
    queryFn: fetchAllProperties,
  });

  return { data, isError, isLoading };
}
