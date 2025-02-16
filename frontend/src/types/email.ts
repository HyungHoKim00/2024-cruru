export interface Email {
  subject: string;
  content: string;
  createdDate: string;
  status: 'PENDING' | 'DELIVERED' | 'FAILED';
}
