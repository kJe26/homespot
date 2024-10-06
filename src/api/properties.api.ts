import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    Accept: 'application/json',
  },
});

export type Property = {
  id: string;
  address: string;
  salePrice: number;
  numberOfRooms: number;
  propertyType: string;
  area: number;
  ownerId: string;
  description?: string;
};

export async function fetchAllProperties(): Promise<Property[]> {
  const res = await api.get<Property[]>('/properties');
  return res.data;
}

export async function fetchProperty(id: string): Promise<Property> {
  const res = await api.get<Property>(`/properties/${id}`);
  return res.data;
}

export type CreateProperty = {
  address: string;
  salePrice: number;
  numberOfRooms: number;
  propertyType: string;
  area: number;
  ownerId: string;
  description: string;
};

export async function createProperty(data: CreateProperty): Promise<Property> {
  const res = await api.post<Property>('/properties', data);
  return res.data;
}

type DeleteData = {
  propertyId: string;
};

export async function deleteProperty(data: DeleteData): Promise<string> {
  const res = await api.delete(`/properties/${data.propertyId}`);
  return res.data;
}

export async function updateProperty(data: Property): Promise<Property> {
  const updateData = {
    address: data.address,
    salePrice: data.salePrice,
    numberOfRooms: data.numberOfRooms,
    propertyType: data.propertyType,
    area: data.area,
    ownerId: data.ownerId,
    description: data.description,
  };
  const res = await api.put<Property>(`/properties/${data.id}`, updateData);
  return res.data;
}
