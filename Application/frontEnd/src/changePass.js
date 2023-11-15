import React from 'react';
import { TextInput, SimpleForm, Toolbar, SaveButton, required } from 'react-admin';

// Custom Save button where the logic of fetch will be
const CustomSaveButton = ({ handleSubmitWithRedirect, ...props }) => {
  const handleClick = () => {
    const pass1 = document.getElementById("newPassword").value;
    const pass2 = document.getElementById("oldPassword").value;
    const email = localStorage.getItem("email");
    const request = new Request("http://localhost:8000/api/profesori/changePassword", {
      method: "POST",
      headers: { "X-Authorization": "Bearer " + localStorage.getItem("token"),
      "Content-type": "application/json; charset=UTF-8" },
      body: JSON.stringify({ pass1, pass2, email}),
    });
    fetch(request)
    .then(response => response.json())
    .then(data => {
        // Process the response data here
        console.log(data);
        // Display a successful pop-up message
        alert("Change password successful.");
    })
    .catch((error) => {console.error('Error:', error);
    alert("Wrong password. Please try again.");

});
  };

  return <SaveButton handleSubmitWithRedirect={handleClick} {...props} />;
};

// Custom toolbar that uses the custom Save button
const CustomToolbar = props => (
  <Toolbar {...props} >
    <CustomSaveButton />
  </Toolbar>
);

export const ListProfesori1 = props => (
  <SimpleForm toolbar={<CustomToolbar {...props}/>}>
    <TextInput validate={[required()]} source="newPassword" label="Old password" type="password" />
    <TextInput validate={[required()]} source="oldPassword" label="New password" type="password" />
  </SimpleForm>
);