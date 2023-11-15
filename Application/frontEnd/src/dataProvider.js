import { stringify } from "query-string";
import {
  GET_LIST,
  GET_ONE,
  CREATE,
  UPDATE,
  DELETE,
  DELETE_MANY,
  GET_MANY,
  fetchUtils
} from "react-admin";

const API_URL = "http://localhost:8000";


const convertDataProviderRequestToHTTP = async (type, resource, params) => {
  const role = localStorage.getItem("role");
  console.log(role);
  console.log(localStorage);
  switch (type) {
    case GET_MANY:
    case GET_LIST:
      if (role === "ROLE_PROFESOR") {
        const profesorId = localStorage.getItem("profesorId");
        if (resource === "cursuri") {
          return {
            url: `${API_URL}/api/${resource}/profesor/${profesorId}`
          };
        } 
        else if (resource === "cursuri") {
          const query = {
            filter: JSON.stringify(params.filter),
          };

          return {
            url: `${API_URL}/api/${resource}/profesor/${profesorId}?${stringify(
              query
            )}`,
          };
        }
        else if(resource === "evidente"){
          const query = {
            filter: JSON.stringify(params.filter),
          };

          return {
            url: `${API_URL}/api/${resource}/profesor/${profesorId}?${stringify(
              query
            )}`,
          };
        }
      } 

      if (resource === "cursuri") {
        const query = {
          filter: JSON.stringify(params.filter),
        };
        localStorage.setItem("profesorId", params.filter.profesor);
        console.log(params.filter.profesor)
        return {
          url: `${API_URL}/api/${resource}?${stringify(query)}`,
        };
      }

      if (resource === "evidente") {
        const query = {
          filter: JSON.stringify(params.filter),
        };
        localStorage.setItem("profesorId", params.filter.profesor);
        console.log(params.filter.profesor)
        return {
          url: `${API_URL}/api/${resource}?${stringify(query)}`,
        };
      }

      return {
        url: `${API_URL}/${resource}`
      };
    case GET_ONE:
      return {
        url: `${API_URL}/${resource}/${params.id}`,
        options: { method: "GET", body: JSON.stringify(params.data) }
      };
    case UPDATE:
      return {
        url: `${API_URL}/api/${resource}/${params.id}`,
        options: { method: "PUT", body: JSON.stringify(params.data) }
      };
    case CREATE:

      return {
        url: `${API_URL}/api/${resource}`,
        options: { method: "POST", body: JSON.stringify(params.data) }
      };
    case DELETE:
      return {
        url: `${API_URL}/api/${resource}/${params.id}`,
        options: { method: "DELETE" }
      };
    case DELETE_MANY: {
      const query = {
        filter: JSON.stringify({ id: params.id })
      };
      return {
        url: `${API_URL}/${resource}?${stringify(query)}`,
        options: { method: "DELETE" }
      };
    }
    default:
      throw new Error(`Unsupported fetch action type ${type}`);
  }
};

const convertHTTPResponseToDataProvider = (
  response,
  type,
  params
) => {
  const { json } = response;
  switch (type) {
    case GET_LIST:
      return {
        data: json.map(x => x),
        total: 1
      };
    case CREATE:
      return { data: { ...params.data, id: json.id } };
    case DELETE_MANY: {
      return { data: json || [] };
    }
    default:
      return { data: json };
  }
};

export default async (type, resource, params) => {
  const { fetchJson } = fetchUtils;
  const { url, options = {} } = await convertDataProviderRequestToHTTP(
    type,
    resource,
    params
  );

  if (!options.headers) {
      options.headers = new Headers({ Accept: 'application/json' });
  }
  const token = localStorage.getItem('token');
  options.headers.set('X-Authorization', 'Bearer ' + token);

  return fetchJson(url, options).then(response =>
    convertHTTPResponseToDataProvider(response, type, resource, params)
  );

};