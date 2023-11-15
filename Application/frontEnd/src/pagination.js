import React from 'react';

import { Pagination } from 'react-admin';

// Giving rowsPerPageOptions the value of an empty array disables pagination
export default props => <Pagination rowsPerPageOptions={[]} {...props} />
