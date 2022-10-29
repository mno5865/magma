/// file/component: User
/// description: A User object based on our EER model
/// Gregory Ojiem - gro3228

export interface User {
  userID: number,
  username: string,
  password: string,
  email: string,
  firstName: string,
  lastName: string,
  creationDate: Date,
  accessDate: Date
}
