import {Breakpoint, Theme, useMediaQuery} from "@mui/material";

export function useScreenSize(size: Breakpoint | number) {
  return useMediaQuery((theme: Theme) => theme.breakpoints.up(size));
}
