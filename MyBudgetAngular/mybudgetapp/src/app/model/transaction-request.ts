export class TransactionRequest {
  description: string;
  amount: number;
  accountId: number;

  constructor(description: string, amount: number, accountId: number) {
    this.description = description;
    this.amount = amount;
    this.accountId = accountId;
  }
}
