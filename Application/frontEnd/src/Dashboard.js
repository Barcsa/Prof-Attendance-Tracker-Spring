import React from 'react';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';

export default () => (
    <Card>
        <CardHeader title={"Buna " + localStorage.getItem('name') + ". Bine ai venit in panoul de administrare!"} />
    </Card>
);