export class Account {
  accountId: number;
  name: string;
  currency: string;
  balance: number;
  defaultCurrencyBalance: number;

  constructor(
    accountId: number,
    name: string,
    currency: string,
    balance: number,
    defaultCurrencyBalance: number
  ) {
    this.accountId = accountId;
    this.name = name;
    this.currency = currency;
    this.balance = balance;
    this.defaultCurrencyBalance = defaultCurrencyBalance;
  }
}
