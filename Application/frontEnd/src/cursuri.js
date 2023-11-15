import React,{useState} from "react";
import PostPagination from "./pagination.js";
import DeleteButton from "./DeleteButton.js";

import {
  List,
  TextField,
  Edit,
  Create,
  Datagrid,
  SimpleForm,
  SelectInput,
  TextInput,
  Filter,
  Responsive,
  ReferenceInput,
  Toolbar,
  SaveButton,
  required
  } from "react-admin";

export const ListCursuri = props => {
  const profesorId = localStorage.getItem("profesorId");
  const role = localStorage.getItem("role");
  const [url, setUrl] = useState(null);
  console.log(localStorage);
  const mystyle = {
    margin: "15px",
    textDecoration: "none",
    color: "#2196f3",
  };
  const imageStyle = {
    height: "20px",
    width: "20px",
    margin:"0 5px 0 0"
  };

  return (
    <>
  <List 
  filters={<FilterProfesor />}
  title="Cursuri" {...props} bulkActions={false} pagination={<PostPagination />}>
    <Responsive
      medium={
        <Datagrid rowClick="edit">
          <TextField source="numeCurs" sortable={false} label="Nume curs" />
          <TextField source="specializare" sortable={false} label="Specializare" />
          <TextField source="an" sortable={false} label="An" />
          <TextField source="profesorName" sortable={false} label="Profesor" />
        </Datagrid>
      }
    />
  </List>
  </>
  );
};

const CursName = ({ record }) => {
  // Prepare data in title compute
  return <span>Editare Curs</span>;
};

const CustomToolbar = props => (
  <Toolbar {...props}>
      <SaveButton/>
      &nbsp;&nbsp;
      <DeleteButton label="Sterge curs" doAction={() => deleteCurs(props)} title="Sterge curs" content="Sunteti sigur ca doriti sa stergeti aceast curs?" />
  </Toolbar>
);


const deleteCurs = ({ record = {} }) => {
    const request = new Request("http://localhost:8000/cursuri/" + record.id + "/delete", {
        method: "GET",
        headers: { "X-Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-type": "application/json; charset=UTF-8" }
      });
      fetch(request)
        .then(response => {
          document.location.href = '#/cursuri/';
        });
}

const FilterProfesor = (props) => (
  <Filter {...props}>
    {localStorage.getItem("role") === "ROLE_ADMIN" && (
      <ReferenceInput
        label="Profesor"
        source="profesor"
        reference="profesori"
        allowEmpty
        alwaysOn
      >
        <SelectInput optionText="name" optionValue="id" />
      </ReferenceInput>
    )}
  </Filter>
);
export const CursEdit = (props) => (  
  <Edit title={<CursName />} {...props}>
    <SimpleForm redirect="list" toolbar={<CustomToolbar />}>

      <ReferenceInput validate={required()} label="Profesor" source="profesorId" reference="profesori">
          <SelectInput optionValue="id" optionText="name" />
      </ReferenceInput>

      <TextInput validate={required()} source="numeCurs" label="Nume curs"  />
      <TextInput validate={required()} source="an" label="An universitar"  />
      <TextInput validate={required()} source="specializare" label="Specializare"  />
    </SimpleForm>
  </Edit>
);

export const CursCreate = props => {

  return (
    <>

    <Create title="Adauga curs" {...props}>
    <SimpleForm redirect="list" toolbar={<CustomToolbar />}>
      <ReferenceInput validate={required()} label="Profesor" source="profesorId" reference="profesori">
          <SelectInput optionValue="id" optionText="name" />
      </ReferenceInput>

      <TextInput validate={required()} source="numeCurs" label="Nume curs"  />
      <TextInput validate={required()} source="an" label="An universitar"  />
      <TextInput validate={required()} source="specializare" label="Specializare"  />
    
    </SimpleForm>
  </Create>
  </>
)};
