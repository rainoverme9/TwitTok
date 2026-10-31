//IMPORT REACT UTILITIES
import { Text, View, TouchableOpacity, StatusBar, ActivityIndicator } from 'react-native';
import React, { useState, useEffect, useContext } from 'react';
//icons
import { FontAwesome } from '@expo/vector-icons';
import { FontAwesome5 } from '@expo/vector-icons';
//navigator
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
//context
import Context from '../CONTEXT/ContextProvider'
//page
import HomePage from './Pagine/HomePage';
import UserInfoPage from './Pagine/UserInfoPage';
import NewTwokPage from './Pagine/NewTwokPage';
import ProfilePage from './Pagine/ProfilePage';
import FollowerPage from './Pagine/FollowerPage';
import Settings from './Pagine/SettingsPage';
import Map from './Utilities/Map';
import NoInternetConnectionPage from './NoInternetConnection/NoInternetConnectionPage';


//NAVIGATOR
const HomeStack = createNativeStackNavigator();
const NewTwokStack = createNativeStackNavigator();
const ProfileStack = createNativeStackNavigator();
const TabMenu = createBottomTabNavigator();

//HOME
function HomeStackScreen() {
    const { globalVar } = useContext(Context)

    return (
        <HomeStack.Navigator
            screenOptions={{
                headerShown: globalVar.network,
                headerStyle: {
                    backgroundColor: 'white',
                },
                headerTintColor: 'black',
                headerTitleStyle: {
                    fontWeight: 'bold',
                },
                headerBackTitleVisible: false,
            }}
        >
            <HomeStack.Screen
                name="Home"
                component={HomeScreen}
                options={() => ({
                    headerTitle: () => (
                        <Text style={{ color: 'white', fontSize: 20, fontWeight: 'bold', color: 'red' }}>Tɯιƚ Tσƙ</Text>
                    ),

                })}
            />
            <HomeStack.Screen
                name="UserInfo"
                component={UserInfoScreen}
                options={({ route }) => ({
                    title: route.params.params.name,
                    headerRight: () => (
                        <FontAwesome5 name="user-plus" size={24} color="black" />
                    ),
                })}
            />
            <HomeStack.Screen
                name="Map"
                component={MapScreen}
                options={({ route }) => ({
                    title: route.params.params.position.posizione,
                    presentation: 'modal'
                })}
            />
        </HomeStack.Navigator>

    );
}
function HomeScreen({ navigation }) {
    const { globalVar } = useContext(Context)

    return (
        <View>
            <View style={{ display: globalVar.network ? 'flex' : 'none' }}>
                <HomePage navigation={navigation}></HomePage>
            </View>
            <View style={{ display: globalVar.network ? 'none' : 'flex', height: '100%', width: '100%' }}>
                <NoInternetConnectionPage></NoInternetConnectionPage>
            </View>
        </View>
    );
}

//NEW TWOK
function NewTwokStackScreen() {
    const { globalVar } = useContext(Context)

    return (
        <NewTwokStack.Navigator
            screenOptions={{
                headerShown: globalVar.network,
                headerStyle: {
                    backgroundColor: 'white',
                },
                headerTintColor: 'black',
                headerTitleStyle: {
                    fontWeight: 'bold',
                },
                headerBackTitleVisible: false,
            }}
        >
            <NewTwokStack.Screen
                name="NewTwok"
                component={NewTwokScreen}
                options={() => ({
                    title: "Nuovo Twok",
                    headerRight: () => (
                        <TouchableOpacity><FontAwesome5 name="paper-plane" size={24} color="black" /></TouchableOpacity>
                    ),
                })}
            />
        </NewTwokStack.Navigator>
    );
}
function NewTwokScreen({ navigation }) {
    const { globalVar } = useContext(Context)
    return (
        <View>
            <View style={{ display: globalVar.network ? 'flex' : 'none' }}>
                <NewTwokPage navigation={navigation}></NewTwokPage>
            </View>
            <View style={{ display: globalVar.network ? 'none' : 'flex', height: '100%', width: '100%' }}>
                <NoInternetConnectionPage></NoInternetConnectionPage>
            </View>
        </View>

    );
}

//PROFILO
function ProfileStackScreen({ navigation }) {
    const { globalVar } = useContext(Context)

    return (
        <ProfileStack.Navigator
            screenOptions={{
                headerShown: globalVar.network,
                headerStyle: {
                    backgroundColor: 'white',
                },
                headerTintColor: 'black',
                headerTitleStyle: {
                    fontWeight: 'bold',
                },
            }}
        >
            <ProfileStack.Screen
                name="Profile"
                component={ProfileScreen}
                options={{
                    title: "Profilo",
                    headerRight: () => (
                        <View style={{ flexDirection: 'row', justifyContent: 'space-around' }}>
                            <FontAwesome5 name="users" size={24} color="black" style={{ paddingRight: 20 }} onPress={() => navigation.navigate('Follower')} />
                            <FontAwesome name="gear" size={24} color="black" onPress={() => navigation.navigate('Settings')} />
                        </View>
                    ),
                }}
            />
            <ProfileStack.Screen
                name="Settings"
                component={SettingsScreen}
                options={{
                    title: "Impostazioni",
                    presentation: 'modal',
                }}
            />
            <ProfileStack.Screen
                name="Follower"
                component={FollowerScreen}
                options={{
                    title: "Utenti seguiti",
                }}
            />
            <ProfileStack.Screen
                name="UserInfo"
                component={UserInfoScreen}
                options={({ route }) => ({
                    title: route.params.params.name,
                    headerRight: () => (
                        <FontAwesome5 name="user-plus" size={24} color="black" />
                    ),
                })}
            />
            <ProfileStack.Screen
                name="Map"
                component={MapScreen}
                options={({ route }) => ({
                    title: route.params.params.position.posizione,
                    presentation: 'modal',
                })}
            />
        </ProfileStack.Navigator>
    );
}
function ProfileScreen({ navigation }) {
    const { globalVar } = useContext(Context)

    return (
        <View>
            <View style={{ display: globalVar.network ? 'flex' : 'none' }}>
                <ProfilePage navigation={navigation}></ProfilePage>
            </View>
            <View style={{ display: globalVar.network ? 'none' : 'flex', height: '100%', width: '100%' }}>
                <NoInternetConnectionPage></NoInternetConnectionPage>
            </View>
        </View>

    );
}
function FollowerScreen({ navigation }) {
    const { globalVar } = useContext(Context)

    return (
        <View>
            <View style={{ display: globalVar.network ? 'flex' : 'none' }}>
                <FollowerPage navigation={navigation}></FollowerPage>
            </View>
            <View style={{ display: globalVar.network ? 'none' : 'flex', height: '100%', width: '100%' }}>
                <NoInternetConnectionPage></NoInternetConnectionPage>
            </View>
        </View>
    );
}
function SettingsScreen({ navigation }) {
    const { globalVar } = useContext(Context)

    return (
        <View>
            <View style={{ display: globalVar.network ? 'flex' : 'none' }}>
                <Settings navigation={navigation}></Settings>
            </View>
            <View style={{ display: globalVar.network ? 'none' : 'flex', height: '100%', width: '100%' }}>
                <NoInternetConnectionPage></NoInternetConnectionPage>
            </View>
        </View>
    );
}

//USER INFO PAGE
function UserInfoScreen({ route, navigation }) {
    const uid = route.params.params.uid
    const { globalVar } = useContext(Context)

    return (
        <View>
            <View style={{ display: globalVar.network ? 'flex' : 'none' }}>
                <UserInfoPage uid={uid} navigation={navigation}></UserInfoPage>
            </View>
            <View style={{ display: globalVar.network ? 'none' : 'flex', height: '100%', width: '100%' }}>
                <NoInternetConnectionPage></NoInternetConnectionPage>
            </View>
        </View>
    );
}
//MAP PAGE
function MapScreen({ route }) {
    //const { globalVar } = useContext(Context)

    position = {
        latitude: route.params.params.position.lat,
        longitude: route.params.params.position.lon,
        latitudeDelta: 0.1,
        longitudeDelta: 0.05
    }
    return (
        <Map position={position}></Map>
    );
}

function Main() {
    const { globalVar } = useContext(Context)

    useEffect(() => {
        if (globalVar.network == false) {
            alert("Nessuna connessione Internet 🌐")
        }
    }, [globalVar.network]);

    return (
        globalVar.sid != null && globalVar.uid != null ?
            <NavigationContainer>
                <TabMenu.Navigator
                    screenOptions={{
                        headerShown: false,
                        tabBarShowLabel: false,
                        tabBarInactiveTintColor: "black",
                        tabBarActiveTintColor: "red",
                        tabBarStyle: {
                            backgroundColor: "white",
                        },
                        tabBarLabelStyle: {
                            marginBottom: 8,
                        },
                    }}>
                    <TabMenu.Screen name="Home_Screen" component={HomeStackScreen} options={{ tabBarBounces: true, tabBarLabel: 'Home', tabBarIcon: ({ color }) => <FontAwesome name="home" size={30} color={color} /> }} />
                    <TabMenu.Screen name="NewTwok_Screen" component={NewTwokStackScreen} options={{ tabBarLabel: 'NewTwok', tabBarIcon: ({ color }) => <FontAwesome name="plus" size={30} color={color} /> }} />
                    <TabMenu.Screen name="Profile_Screen" component={ProfileStackScreen} options={{ tabBarLabel: 'Profile', tabBarIcon: ({ color }) => <FontAwesome5 name="user-alt" size={24} color={color} /> }} />
                </TabMenu.Navigator>
            </NavigationContainer>
            :
            globalVar.network ?
                <View style={{ height: '100%', width: '100%', flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: 'white' }}>
                    <StatusBar barStyle='dark-content'></StatusBar>
                    <Text style={{ color: 'white', fontSize: 20, fontWeight: 'bold', color: 'red' }}>Tɯιƚ Tσƙ</Text>
                    <ActivityIndicator size="large" />
                </View>
            :
                <View style={{ height: '100%', width: '100%', flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: 'white' }}>
                    <StatusBar barStyle='dark-content'></StatusBar>
                    <Text style={{ color: 'white', fontSize: 20, fontWeight: 'bold', color: 'red' }}>Tɯιƚ Tσƙ</Text>
                    <Text>Nessuna connessione Internet 🌐</Text>
                </View>

    );
}

export default Main;