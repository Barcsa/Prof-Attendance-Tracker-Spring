import React, { Fragment, useState } from 'react';
import {
    Confirm
} from 'react-admin';
import Button  from "@material-ui/core/Button";
import DeleteIcon from "@material-ui/icons/Delete";

const DeleteButton = ({ doAction, title, content, label }) => {
    const [open, setOpen] = useState(false);
    const handleClick = () => setOpen(true);
    const handleDialogClose = () => setOpen(false);

    const handleDelete = () => {
        doAction();
        setOpen(false);
    };

    return (
        <Fragment>
            <Button variant="contained" color="error" startIcon={<DeleteIcon />} onClick={handleClick}>{label}</Button>
            <Confirm
                isOpen={open}
                title={title}
                content={content}
                confirm="Da"
                confirmColor="primary"
                cancel="Nu"
                onConfirm={handleDelete}
                onClose={handleDialogClose}
            />
        </Fragment>
    );
}

export default DeleteButton;