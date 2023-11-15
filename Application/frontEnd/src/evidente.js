import React,{useState} from "react";
import PostPagination from "./pagination.js";
import DeleteButton from "./DeleteButton.js";

import {
  List,
  Edit,
  Create,
  Datagrid,
  SimpleForm,
  SelectInput,
  Filter,
  TextField,
  Responsive,
  TextInput,
  DateInput,
  ReferenceInput,
  Toolbar,
  SaveButton,
  number,
  required
  } from "react-admin";

export const ListEvidente = props => {
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
  title="Evidente" {...props} bulkActions={false} pagination={<PostPagination />}>
     <Responsive
      medium={
        <Datagrid rowClick="edit">
          <TextField source="cursName" sortable={false} label="Nume curs" />
          <TextField source="an" sortable={false} label="An universitar" />
          <TextField source="profesorName" sortable={false} label="Nume profesor" />
          <TextField source="data" sortable={false} label="Data" />
          <TextField source="intreOre" sortable={false} label="Intre orele" />
          <TextField source="nrOreCurs" sortable={false} label="Numar ore curs" />
          <TextField source="nrOreSLP" sortable={false} label="Numar ore SLP" />
        </Datagrid>
      }
    />
  </List>
  </>
  );
};

const EvidentaName = ({ record }) => {
  // Prepare data in title compute
  return <span>Editare evidenta</span>;
};

const CustomToolbar = props => (
  <Toolbar {...props}>
      <SaveButton/>
      &nbsp;&nbsp;
      <DeleteButton label="Sterge evidenta" doAction={() => deleteEvidenta(props)} title="Sterge evidenta" content="Sunteti sigur ca doriti sa stergeti aceasta evidenta?" />
  </Toolbar>
);


const deleteEvidenta = ({ record = {} }) => {
    const request = new Request("http://localhost:8000/evidente/" + record.id + "/delete", {
        method: "GET",
        headers: { "X-Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-type": "application/json; charset=UTF-8" }
      });
      fetch(request)
        .then(response => {
          document.location.href = '#/evidente/';
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
export const EvidentaEdit = (props) => (  
  <Edit title={<EvidentaName />} {...props}>
   <SimpleForm redirect="list" toolbar={<CustomToolbar />}>

<ReferenceInput validate={required()} label="Curs" source="cursId" reference="cursuri">
    <SelectInput optionValue="id" optionText="numeCurs" />
</ReferenceInput>

      <DateInput validate={required()} source="data" label="Data" />
      <TextInput validate={required()} source="intreOre" label="Interval orar"  />
      <TextInput validate={[required(),number()]} source="nrOreCurs" label="Numar de ore curs"  />
      <TextInput validate={[required(),number()]} source="nrOreSLP" label="Numar de ore SLP"  />
</SimpleForm>
  </Edit>
);

export const EvidentaCreate = props => {

  return (
    <>

    <Create title="Adauga evidenta" {...props}>
    <SimpleForm redirect="list" toolbar={<CustomToolbar />}>
      <ReferenceInput validate={required()} label="Curs" source="cursId" reference="cursuri">
          <SelectInput optionValue="id" optionText="numeCurs" />
      </ReferenceInput>

      {/* <TextInput validate={required()} source="data" label="Data"  /> */}
      <DateInput validate={required()} source="data" label="Data" />
      <TextInput validate={required()} source="intreOre" label="Interval orar"  />
      <TextInput validate={[required(),number()]} source="nrOreCurs" label="Numar de ore curs"  />
      <TextInput validate={[required(),number()]} source="nrOreSLP" label="Numar de ore SLP"  />
    </SimpleForm>
  </Create>
  </>
)};
