import * as React from 'react';
import ListSubheader from '@mui/material/ListSubheader';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import DraftsIcon from '@mui/icons-material/Drafts';
import SendIcon from '@mui/icons-material/Send';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import StarBorder from '@mui/icons-material/StarBorder';
import {IconButton, ListItem} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

interface Props{
    title: string;
    icon?: React.ReactNode;

    children: React.ReactNode[]
}

// interface ChildrenProps {
//     title: string;
//     icon:React.ReactNode;
// }
export default function ExpandListItem({title, icon, children}: Props) {
    const [open, setOpen] = React.useState(false);

    const handleClick = () => {
        setOpen(!open);
    };

    return (
        <>
            <ListItemButton onClick={handleClick}>
                {/*<ListItemIcon>
                    {icon}
                </ListItemIcon>*/}
                <ListItemText primary={title}/>
                {open ? <ExpandLess/> : <ExpandMore/>}
            </ListItemButton>
            <Collapse in={open} timeout="auto" unmountOnExit>
                <List component="div" disablePadding>
                    {/*{children.map((c, index)=>{
                        return (
                            <ListItemButton key={index} sx={{pl: 4}}>
                                <ListItemIcon>
                                    {c.icon}
                                </ListItemIcon>
                                <ListItemText primary={c.title}/>
                            </ListItemButton>
                        )
                    })}*/}
                    {children}
                </List>
            </Collapse>
        </>
    );
}