
//IMPORT REACT UTILITIES
import React, { useState, useEffect, useContext, useRef } from "react";
import { ActivityIndicator, Dimensions, FlatList, StyleSheet, Text, View } from "react-native";
//variable
import { useBottomTabBarHeight } from '@react-navigation/bottom-tabs';
import { useHeaderHeight } from '@react-navigation/elements';
//scroll to top
import { useScrollToTop } from '@react-navigation/native';
//page
import UserInfo from '../Utilities/UserInfo';
//class
import UserClass from '../../CLASS/UserClass';
//context
import Context from '../../CONTEXT/ContextProvider'

//Pagina che mostra la lista dei follower dell'utente loggato
function FollowerPage(props) {
    const { globalVar } = useContext(Context)
    
    //const follower
    const [followers, setfollowers] = useState([]);
    //loading-refresh
    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = useState(true);
    //variable
    const tabBarHeight = useBottomTabBarHeight();
    const headerHeight = useHeaderHeight();

    //USEEFFECT
    //Download dela lista dei follower
    useEffect(() => { getAllFollower() }, []);

    //FUNZIONI
    //Ritorna la lista dei follower
    function getAllFollower() {
        UserClass.getAllFollower(globalVar.sid)
            .then(result => { setfollowers(result), setLoading(false), setRefreshing(false) })
            .catch(error =>  alert("Si è verifica un errore durante il download della lista degli utenti seguiti"))
    }

    //Ritorna all'inizio della lista dei follower
    const ref = useRef(null);
    useScrollToTop(ref);
    
    //Effettua il refresh della lista dei follower
    function onRefresh() {
        getAllFollower()
    }

    return (
        <View style={{height: Dimensions.get('window').height - headerHeight - tabBarHeight}}>
            {loading == true ?
                <View style={styles.indicatorWrapper}>
                    <ActivityIndicator size="large"/>
                </View>
                :
                <View>
                    {refreshing ? <ActivityIndicator size="small" /> : null}
                    <FlatList
                        data={followers}
                        renderItem={(follower) => { return (<UserInfo uid={follower.item.uid} name={follower.item.name} pversion={follower.item.pversion} screen={'Follower_Screen'} navigation={props.navigation} ></UserInfo>) }}
                        snapToInterval={Dimensions.get('window').height}
                        snapToAlignment="start"
                        decelerationRate="fast"
                        ListEmptyComponent={<View style={[styles.EmptytextHeader(headerHeight, tabBarHeight)]}><Text style={styles.EmptyMassage}>Nessun utente ancora seguito</Text></View>}
                        refreshing={refreshing}
                        ref={ref}
                        onRefresh={() => onRefresh()}
                    />
                </View>
            }
        </View>
    );
};

const styles = StyleSheet.create({
    indicatorWrapper: {
        backgroundColor: 'white',
        flex: 1,
        alignItems:'center',
        justifyContent: 'center',
    },
    EmptytextHeader: (headerHeight, tabBarHeight) => ({
        height: Dimensions.get('window').height - headerHeight - tabBarHeight,
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'white'
    }),
    EmptyMassage: {
        color: 'red',
        fontWeight: '700',
        fontSize: 16,
        fontStyle: 'normal',
    },
});

export default FollowerPage;