import { Account } from './account';

export class Transaction {
  transactionId: number;
  description: string;
  amount: number;
  defaultCurrencyAmount: number;
  account: Account;

  constructor(
    transactionId: number,
    description: string,
    amount: number,
    defaultCurrencyAmount: number,
    account: Account
  ) {
    this.transactionId = transactionId;
    this.description = description;
    this.amount = amount;
    this.defaultCurrencyAmount = defaultCurrencyAmount;
    this.account = account;
  }
}
