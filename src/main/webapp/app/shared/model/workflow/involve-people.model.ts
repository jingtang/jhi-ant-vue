export interface IInvolvePeople {
  url?: string;
  user?: string;
  group?: string;
  type?: string;
}

export class InvolvePeople implements IInvolvePeople {
  constructor(public url?: string, public user?: string, public group?: string, public type?: string) {}
}
