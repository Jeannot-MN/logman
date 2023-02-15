// TODO: if we don't provide en-ZA user will see ZAR instead of R
// TODO: better locale detection
const locale = 'en-ZA';

function isMax(val: number) {
  return val >= 2147483647;
}

export class FormatterService {
  public static currency(
    value: number,
    _symbol?: string,
    places?: number
  ): string {
    return new Intl.NumberFormat('en-ZA', {
      style: 'currency',
      // TODO: change this out
      currency: 'ZAR',
      maximumFractionDigits: places,
      minimumFractionDigits: places,
      currencyDisplay: 'symbol',
    }).format(value);
  }

  public static currencyNoSpace(
    value: number,
    _symbol?: string,
    places?: number
  ): string {
    return new Intl.NumberFormat('en-ZA', {
      // style: 'currency',
      // TODO: change this out
      // currency: 'ZAR',
      maximumFractionDigits: places,
      minimumFractionDigits: places,
      // currencyDisplay: 'symbol',
    }).format(value);
  }

  public static decimal(value: number, places?: number): string {
    return value.toLocaleString(locale, {
      style: 'decimal',
      maximumFractionDigits: places,
    });
  }

  public static integer(value: number): string {
    return value.toLocaleString(locale, {
      style: 'decimal',
      maximumFractionDigits: 0,
    });
  }

  public static currencyRange(min: number, max: number, places = 0): string {
    return `${FormatterService.currency(min, undefined, places)} ${
      isMax(max)
        ? '+'
        : ` - ${FormatterService.currency(max, undefined, places)}`
    }`;
  }

  public static metricIntegerRange(
    min: number,
    max: number,
    metric: string
  ): string {
    return `${FormatterService.integer(min)} ${
      isMax(max) ? '+' : `- ${FormatterService.integer(max)} ${metric}`
    }`;
  }
}
