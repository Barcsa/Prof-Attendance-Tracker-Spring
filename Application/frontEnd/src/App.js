import React from "react";
import { Admin, Resource, Login } from "react-admin";
import Dashboard from "./Dashboard";
import { ListCursuri, CursEdit, CursCreate } from "./cursuri.js";
import { ListProfesori, ProfesorEdit, ProfesorCreate } from "./profesori.js";
import { ListEvidente, EvidentaEdit, EvidentaCreate } from "./evidente.js";
import { ListProfesori1 } from "./changePass.js";

import NotFound from "./notFound";
import { createMuiTheme } from "@material-ui/core/styles";
import blueGrey from "@material-ui/core/colors/blueGrey";
import blue from "@material-ui/core/colors/blue";
import green from "@material-ui/core/colors/lightGreen";
import authProvider from "./authProvider";
import dataProvider from "./dataProvider.js";

import SchoolIcon from '@material-ui/icons/School';
import PersonPinIcon from '@material-ui/icons/PersonPin';
import englishMessages from 'ra-language-english';

const theme = createMuiTheme({
  palette: {
    primary: blue,
    secondary: blueGrey,
    error: green,
    contrastThreshold: 3,
    tonalOffset: 0.2
  }
});

const MyLoginPage = () => (
  <Login
      // A random image that changes everyday
      backgroundImage=""
  />
);

const infoMessages = {
  ra: {
      notification: {
          http_error: 'Eroare de conexiune. Va rugam sa mai incercati.',
      },
      action: {
          save: 'Salveza',
          delete: 'Sterge',
          show: 'Vizualizeaza',
          create: 'Adauga',
          cancel: 'Anuleaza',
          edit: 'Administreaza',
          list: 'Vizualizeaza',
          export: 'Exporta',
          search: 'Cauta',
          refresh: 'Actualizeaza'
      },
      boolean: {
          true: 'Da',
          false: 'Nu',
          null: '',
      },
      page: {
          create: 'Adauga %{name}',
          dashboard: 'Pagina principala',
          error: 'Ceva nu a mers bine. Va rugam sa mai incercati',
          loading: 'Se incarca',
          not_found: 'Nu a fost gasit'
      },
      auth: {
          auth_check_error: 'Va rugam sa va autentificati pentru a continua',
          user_menu: 'Profil',
          username: 'Nume utilizator',
          password: 'Parola',
          sign_in: 'Autentificare',
          sign_in_error: 'Autentificare esuata! Va rugam sa mai incercati.',
          logout: 'Deconectare',
      },
      validation: {
          required: 'Necesar',
          minLength: 'Numarul de telefon este incorect',
          maxLength: 'Numarul de telefon este incorect',
          minValue: 'Trebuie sa fie cel putin %{min}',
          maxValue: 'Trebuie sa fie cel mult %{max} sau mai putin',
          number: 'Trebuie sa fie un numar',
          email: 'Trebuie sa fie un email valid',
          regex: 'Trebuie sa se potrunaiveasca cu formatul: %{pattern}',
      },
  },
};

const messages = {
  ro: infoMessages,
};

const i18nProvider = locale => messages[locale];

const App = () => (
  <Admin locale="ro" i18nProvider={i18nProvider}
    loginPage={MyLoginPage}
    theme={theme}
    catchAll={NotFound}
    dashboard={Dashboard}
    dataProvider={dataProvider}
    title="Panou administrare"
    authProvider={authProvider}
  >
    { role => [
      <Resource
        name="cursuri"
        list={ListCursuri}
        edit={role === "ROLE_ADMIN" ? CursEdit : null}
        create={role === "ROLE_ADMIN" ? CursCreate : null}
        options={{ label: "Cursuri" }}
        icon={SchoolIcon}
      />,
      <Resource
      name="evidente"
      list={ListEvidente}
      edit={EvidentaEdit}
      create={EvidentaCreate}
      options={{ label: "Evidente" }}
      icon={SchoolIcon}
    />,
    <Resource
      name="changePass"
      list={ListProfesori1}
      options={{ label: "Change password" }}
      icon={SchoolIcon}
    />,
      role === "ROLE_ADMIN" ? (
        <Resource
          name="profesori"
          list={ListProfesori}
          edit={ProfesorEdit}
          create={ProfesorCreate}
          options={{ label: "Profesori" }}
          icon={PersonPinIcon}
        />
      ) : null,
     
    ]}
  </Admin>
);

export default App;