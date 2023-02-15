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
import GroupIcon from '@mui/icons-material/Group';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import UploadFileIcon from '@mui/icons-material/UploadFile';
import ArticleIcon from '@mui/icons-material/Article';

import {
    AppBar, Avatar, Badge,
    Box,
    Button, createStyles,
    CssBaseline, Divider,
    Drawer,
    IconButton, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Theme,
    Toolbar,
    Typography
    , useTheme,
    withStyles
} from "@mui/material";
import { makeStyles } from 'tss-react/mui';
import ExpandListItem from "../ExpandListItem/ExpandListItem";
import BusinessIcon from '@mui/icons-material/Business';
import ApartmentIcon from '@mui/icons-material/Apartment';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import GpsFixedIcon from '@mui/icons-material/GpsFixed';
import ExpandLess from "@mui/icons-material/ExpandLess";
import ExpandMore from "@mui/icons-material/ExpandMore";
import RouteIcon from '@mui/icons-material/Route';
import SignalCellularAltIcon from '@mui/icons-material/SignalCellularAlt';
import PriorityHighIcon from '@mui/icons-material/PriorityHigh';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CategoryIcon from '@mui/icons-material/Category';

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
            '&::-webkit-scrollbar':{
                display: 'none'
            }
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
            height: '72.5px',
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
                     'temporary'
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
                        <ExpandListItem title={"Admin"}>
                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <BusinessIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Company"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <ApartmentIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Departments"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <GpsFixedIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Sites"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <GroupIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Users"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <AccessTimeIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Shifts"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <LocalShippingIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Vehicles"/>
                            </ListItemButton>
                        </ExpandListItem>
                        <ExpandListItem title={"Resources"}>
                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <UploadFileIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Upload Document"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <ArticleIcon/>
                                </ListItemIcon>
                                <ListItemText primary="View Documents"/>
                            </ListItemButton>
                        </ExpandListItem>
                        <ExpandListItem title={"Logistics"}>
                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <LocalShippingIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Deliveries"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <RouteIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Routes"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <CategoryIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Item Management"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <StorefrontIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Order Request"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <PriorityHighIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Alerts"/>
                            </ListItemButton>

                            <ListItemButton sx={{pl: 4}}>
                                <ListItemIcon>
                                    <SignalCellularAltIcon/>
                                </ListItemIcon>
                                <ListItemText primary="Reports"/>
                            </ListItemButton>
                        </ExpandListItem>
                        <ListItemButton
                            onClick={()=>{
                                handleLogout();
                                navigate('/login');}
                            }
                        >
                            <ListItemText primary="Logout"/>
                            <ExitToAppIcon/>
                        </ListItemButton>
                    </List>
                ) : null}

                {auth.authenticated ? (
                    <List style={{ display: 'flex', justifyContent: 'flex-end' }}>
                        <ListItem
                            style={{ paddingRight: '48px' }}
                            button
                            onClick={(event) => {
                                handleNavigation('/profile', event, 6);
                            }}
                        >
                            <ListItemIcon>
                                <Suspense fallback="Loading..">
                                    <Box>
                                        <Avatar
                                            /*src={auth.user.profileImageUri || ''}
                                            alt={auth.user.profileImageUri || ''}*/
                                            src={''}
                                            alt={''}
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
