import React, { Suspense, useContext } from 'react';
import MenuIcon from '@mui/icons-material/Menu';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ListIcon from '@mui/icons-material/List';
import { useNavigate } from 'react-router';
import clsx from 'clsx';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import MeetingRoomIcon from '@mui/icons-material/MeetingRoom';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import { useAuthContext } from '../../context/AuthContext';
import { useScreenSize } from '../../hooks/useScreenSize';

import {
    AppBar, Avatar, Badge,
    Box,
    Button, createStyles,
    CssBaseline, Divider,
    Drawer,
    IconButton, List, ListItem, ListItemIcon, ListItemText, Theme,
    Toolbar,
    Typography
    , useTheme,
    withStyles
} from "@mui/material";
import { makeStyles } from 'tss-react/mui';

const drawerWidth = 300;

/*const StyledBadge = withStyles((theme: Theme) =>
    createStyles({
        badge: {
            right: -3,
            top: 13,
            border: `2px solid ${theme.palette.background.paper}`,
            padding: '0 4px',
            backgroundColor: '#276552',
        },
    })
)(Badge);*/
const useStyles = makeStyles()((theme) => {
    return {
        root: {
            display: 'flex',
        },
        appBar: {
            zIndex: theme.zIndex.drawer + 1,
            transition: theme.transitions.create(['width', 'margin'], {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
            }),
        },
        appBarShift: {
            marginLeft: drawerWidth,
            width: `calc(100% - ${drawerWidth}px)`,
            transition: theme.transitions.create(['width', 'margin'], {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.enteringScreen,
            }),
        },
        menuButton: {
            marginRight: 36,
            marginLeft: 0,
        },
        hide: {
            display: 'none',
        },
        drawer: {
            width: drawerWidth,
            flexShrink: 0,
            whiteSpace: 'nowrap',
        },
        drawerOpen: {
            width: drawerWidth,
            transition: theme.transitions.create('width', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.enteringScreen,
            }),
            overflowX: 'hidden',
        },
        drawerClose: {
            transition: theme.transitions.create('width', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
            }),
            overflowX: 'hidden',
            width: theme.spacing(7) + 1,
            // [theme.breakpoints.up('sm')]: {
            //     width: theme.spacing(9) + 1,
            // },
        },
        toolbar: {
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'flex-end',
            height: '100px',
            padding: theme.spacing(0, 1),
        },
        content: {
            flexGrow: 1,
            padding: theme.spacing(3),
        },
        logo: {
            padding: '20 !important',
            '&:hover': {
                backgroundColor: 'white',
                cursor: 'pointer',
                textDecoration: 'none',
            },
        },
    };
});

export function Header() {
    const navigate = useNavigate();
    const {classes} = useStyles();
    const theme = useTheme();
    const isDesktop = useScreenSize(600);
    const { auth, handleLogout } = useAuthContext();
    const [open, setOpen] = React.useState(false);
    const [selectedIndex, setSelectedIndex] = React.useState(0);

    const handleDrawerClose = () => {
        setOpen(false);
    };

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleNavigation = (
        location: string,
        event: React.MouseEvent<HTMLElement>,
        index: number
    ) => {
        navigate(location);
        setSelectedIndex(index);
        // setSubSelectedIndex(0);
        handleDrawerClose();
    };

    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar
                position="fixed"
                className={clsx(classes.appBar, {
                    [classes.appBarShift]: open,
                })}
            >
                <Toolbar
                    style={{
                        paddingLeft: '16px',
                        paddingBottom: '10px',
                        paddingTop: '10px',
                        borderBottom: '5px solid #276552',
                        backgroundColor: 'white',
                    }}
                >
                    {auth.authenticated ? (
                        <IconButton
                            color="primary"
                            aria-label="open drawer"
                            onClick={handleDrawerOpen}
                            edge="start"
                            className={clsx(classes.menuButton, {
                                [classes.hide]: open,
                            })}
                        >
                            <MenuIcon />
                        </IconButton>
                     ) : null}

                    <IconButton
                        onClick={() => {
                            navigate('/');
                        }}
                        className={classes.logo}
                    >
                        {/*<img
                            style={{ width: '150px' }}
                            src={
                                TakeMyFarmLogo
                            }
                            alt="Take my farm"
                        />*/}
                        <Typography
                            sx={{
                                fontFamily: 'Handlee, cursive',
                                fontWeight: 'bold',
                                fontSize: '32px',
                                color: 'black',
                                fontStyle: 'italic'
                            }}
                        >
                            LogMan
                        </Typography>
                    </IconButton>
                    <Box display="flex" justifyContent="flex-end" width="100%">
                        {auth.authenticated ? (
                            <Box>
                                <Typography
                                    style={{
                                        color: '#000000',
                                        fontWeight: 'bold'
                                    }}
                                >

                                </Typography>
                            </Box>
                        ) : (
                            <Box>
                                {isDesktop ? (
                                    <Box>
                                        {/*<Button
                                            variant="contained"
                                            color="primary"
                                            onClick={() => {
                                                navigate('/register');
                                            }}
                                        >
                                            Sign Up
                                        </Button>*/}
                                        <Button
                                            style={{ marginLeft: '5px' }}
                                            color="primary"
                                            onClick={() => {
                                                navigate('/login');
                                            }}
                                        >
                                            Help
                                        </Button>
                                    </Box>
                                ) : (
                                    <Box>
                                        <IconButton
                                            style={{ color: 'white' }}
                                            aria-label="Sign In"
                                            onClick={() => {
                                                navigate('/register');
                                            }}
                                        >
                                            <PersonAddIcon style={{ fontSize: '28px' }} />
                                        </IconButton>
                                        <IconButton
                                            style={{ color: 'white', marginLeft: '10px' }}
                                            aria-label="Sign Up"
                                            onClick={() => {
                                                navigate('/login');
                                            }}
                                        >
                                            <ExitToAppIcon style={{ fontSize: '28px' }} />
                                        </IconButton>
                                    </Box>
                                )}
                            </Box>
                        )}
                    </Box>
                </Toolbar>
            </AppBar>
            <Drawer
                open={open}
                anchor="left"
                variant={
                    auth.authenticated
                        ? isDesktop
                            ? 'permanent'
                            : 'temporary'
                        : 'temporary'
                }
                className={clsx(classes.drawer, {
                    [classes.drawerOpen]: open,
                    [classes.drawerClose]: !open,
                })}
                classes={{
                    paper: clsx({
                        [classes.drawerOpen]: open,
                        [classes.drawerClose]: !open,
                    }),
                }}
            >
                <div className={classes.toolbar}>
                    <IconButton onClick={handleDrawerClose}>
                        {theme.direction === 'rtl' ? (
                            <ChevronRightIcon />
                        ) : (
                            <ChevronLeftIcon />
                        )}
                    </IconButton>
                </div>
                <Divider />

                {auth.authenticated ? (
                    <List style={{ height: '100%' }}>
                        <ListItem
                            selected={selectedIndex === 2}
                            button
                            onClick={(event) => {
                                handleNavigation('/products', event, 2);
                            }}
                        >
                            <ListItemIcon>
                                <IconButton aria-label="My Products">
                                    <ListIcon />
                                </IconButton>
                            </ListItemIcon>
                            <ListItemText primary="My Products" />
                        </ListItem>

                        <ListItem
                            selected={selectedIndex === 3}
                            button
                            onClick={(event) => {
                                handleNavigation('/purchases', event, 3);
                            }}
                        >
                            <ListItemIcon>
                                <IconButton aria-label="Purchases">
                                    <ShoppingCartIcon />
                                </IconButton>
                            </ListItemIcon>
                            <ListItemText primary="Purchases" />
                        </ListItem>

                        <ListItem
                            selected={selectedIndex === 4}
                            button
                            onClick={(event) => {
                                handleLogout();
                                navigate('/login');
                            }}
                        >
                            <ListItemIcon>
                                <IconButton aria-label="Logout">
                                    <ExitToAppIcon />
                                </IconButton>
                            </ListItemIcon>
                            <ListItemText primary="Logout" />
                        </ListItem>
                    </List>
                ) : null}

                {auth.authenticated ? (
                    <List style={{ display: 'flex', justifyContent: 'flex-end' }}>
                        <ListItem
                            style={{ paddingRight: '48px' }}
                            selected={selectedIndex === 6}
                            button
                            onClick={(event) => {
                                handleNavigation('/profile', event, 6);
                            }}
                        >
                            <ListItemIcon>
                                <Suspense fallback="Loading..">
                                    <Box>
                                        <Avatar
                                            src={auth.user.profileImageUri || ''}
                                            alt={auth.user.profileImageUri || ''}
                                        />
                                    </Box>
                                </Suspense>
                            </ListItemIcon>
                            <ListItemText primary="Profile" />
                        </ListItem>
                    </List>
                ) : (
                    ''
                )}
            </Drawer>
        </div>
    );
}
