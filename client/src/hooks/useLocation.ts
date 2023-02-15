import useGeolocation from 'react-use/lib/useGeolocation';

export function useLocation() {
  return useGeolocation({
    enableHighAccuracy: true,
    // maximumAge?: number;
    // timeout?: number;
  });
}
