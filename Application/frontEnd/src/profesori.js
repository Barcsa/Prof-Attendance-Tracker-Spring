import React from "react";
import PostPagination from "./pagination.js";
import DeleteButton from "./DeleteButton.js";


import {
  List,
  TextField,
  Edit,
  TextInput,
  Create,
  Datagrid,
  SimpleForm,
  Responsive,
  required,
  minLength,
  number,
  email,
  maxLength,
  PasswordInput,
  Toolbar,
  SaveButton,

} from "react-admin";
import { Phone } from "@material-ui/icons";


const boxProps = {
  bgcolor: 'background.paper',
  borderColor: 'text.primary',
  m: 1,
  border: 1
};

export const ListProfesori = props => (
  <List title="Profesori" {...props} bulkActions={false} pagination={<PostPagination />}>
    <Responsive
      medium={
        <Datagrid rowClick="edit">
          <TextField source="name" label="Nume"  />
          <TextField source="mail" label="Adresa mail"  />
          <TextField source="phone" label="Numar de telefon"  />
        </Datagrid>
      }
    />
  </List>
);

const ProfesorName = () => {

  return <span>Editeaza Profesor</span>;
};

const CustomToolbar = props => (
  <Toolbar {...props}>
      <SaveButton />
      <DeleteButton label="Sterge profesor" doAction={() => deleteProfesor(props)} title="Sterge profesor" content="Sunteti sigur ca doriti sa stergeti aceast profesor?" />
  </Toolbar>
);

export const ProfesorEdit = props => (
  <Edit title={<ProfesorName />} {...props}>
    <SimpleForm toolbar={<CustomToolbar />} redirect="list">
      <TextInput validate={required()} source="name" label="Nume"  />
      <TextInput validate={required()} source="phone" label="Numar de telefon"  />
      <TextField source="mail" label="Email"  />
    
    </SimpleForm>
  </Edit>
);

export const ProfesorCreate = props => (
  <Create title="Adauga profesor" {...props}>
    <SimpleForm redirect="list">
      <TextInput validate={required()} source="name" label="Nume" />
      <TextInput validate={[required(), minLength(10),maxLength(10),number()]} source="phone" label="Numar de telefon" />
      <TextInput validate={[required(), email()]} source="mail" label="Email" />
      <TextInput secureTextEntry={true} validate={required()} source="password" label="Password" />

    </SimpleForm>
  </Create>
);

const deleteProfesor = ({ record = {} }) => {
  const request = new Request("http://localhost:8000/profesori/" + record.id + "/delete", {
      method: "GET",
      headers: { "X-Authorization": "Bearer " + localStorage.getItem("token"),
      "Content-type": "application/json; charset=UTF-8" }
    });
    fetch(request)
      .then(response => {
        document.location.href = '#/profesori/';
      });
}
