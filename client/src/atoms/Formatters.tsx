import React from 'react';

import {FormatterService} from '../services/FormatterService';

interface Props {
  children: number | string;
  places?: number;
  symbol?: string;
}
export function Currency({children, places = 0}: Props) {
  if (children != null) {
    if (typeof children === 'string') {
      try {
        return (
          <>
            {FormatterService.currency(parseInt(children), undefined, places)}
          </>
        );
      } catch (error) {
        return <>Number in wrong format</>;
      }
    } else {
      return <>{FormatterService.currency(children, undefined, places)}</>;
    }
  }
  return <></>;
}

export function Decimal({
  children,
  places,
}: {
  children: number;
  places?: number;
}) {
  if (children != null) {
    return <>{FormatterService.decimal(children, places)}</>;
  }

  return null;
}

export function Distance({
  children,
  places = 0,
  metric = 'KM',
}: {
  children: number;
  places?: number;
  metric?: string;
}) {
  if (children != null) {
    return (
      <>
        <Decimal places={places}>{children}</Decimal> {metric}
      </>
    );
  }

  return null;
}

export function Integer({children}: {children: number}) {
  if (children != null) {
    return <>{FormatterService.integer(children)}</>;
  }

  return null;
}

/*export function Date({children}: {children: string}) {
  if (children != null) {
    return <ReactMoment format="YYYY-MM-DD">{children}</ReactMoment>;
  }
  return null;
}

export function DateTime({
  children,
  format = 'YYYY-MM-DD HH:mm:ss',
}: {
  children: string | number;
  format?: string;
}) {
  if (children != null) {
    return <ReactMoment format={format}>{children}</ReactMoment>;
  }
  return null;
}

export function Time({
  children,
  format = 'HH:mm:ss',
}: {
  children: string;
  format?: string;
}) {
  if (children != null) {
    return <ReactMoment format={format}>{children}</ReactMoment>;
  }
  return null;
}*/
