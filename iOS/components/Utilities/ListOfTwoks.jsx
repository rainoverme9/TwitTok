
//IMPORT REACT UTILITIES
import React, { useState, useEffect, useContext, useRef } from 'react';
import { ActivityIndicator, Dimensions, FlatList, StyleSheet, View, Text } from 'react-native';
//variable
import { useBottomTabBarHeight } from '@react-navigation/bottom-tabs';
import { useHeaderHeight } from '@react-navigation/elements';
//scroll to top
import { useScrollToTop } from '@react-navigation/native';
//utilities
import TwokPost from './TwokPost';
//class
import TwokClass from '../../CLASS/TwokClass';
//context
import Context from '../../CONTEXT/ContextProvider'

//Crea una lista dei twok
//1) se l'UID di un utente viene specificato allora mostra solo i twok di quell'utente
//2) mostra i twok in ordine casuale o da un certo twok in poi in base al tidSequence specificato nella pagina in cui viene richiamato
function ListOfTwoks(props) {

    //globalVar
    const { globalVar } = useContext(Context)

    //UID dell'utente, se viene passato
    const uid = props.uid
    //tidSequence
    const [tidSequence, setTidSequence] = useState(props.tidSequence)
    //twok
    const [twoks, setTwoks] = useState([]);
    //loading-refreshing
    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = useState(true);
    //variable
    const tabBarHeight = useBottomTabBarHeight(); 
    const headerHeight = useHeaderHeight();

    //USEEFFCT
    //Mostra la il buffer iniziale dei twok
    useEffect(() => { showToks() }, []);
    
    /*
    useEffect(() => { 
        if (globalVar.sid != null && globalVar.uid != null) {
        showToks();
    }}, [globalVar.sid, globalVar.uid]);
    */
   
    //FUNZIONI
    //Restituisce il buffer iniziale costituito da 5 twok
    //Casuale o da un certo twok in poi in base al tidSequence
    //L'UID può essere nullo
    function showToks() {
        if (tidSequence == -1) {
            TwokClass.downloadInitialBufferTwoks(globalVar.sid, uid)                           
                .then(result => { setTwoks(result), setLoading(false), setRefreshing(false) })
                .catch(error => { 
                    if (error.message === "no twok found with given params") {
                        setLoading(false), setRefreshing(false)
                    } else {
                        alert("Si è verificato un errore durante il download dei twok dal server")
                    }}
                ) 
        } else {
            TwokClass.downloadInitialBufferSpecificTwoks(globalVar.sid, tidSequence)           
                .then(result => { setTwoks(result), setLoading(false), setRefreshing(false), setTidSequence(tidSequence+5) })
                .catch(error => {
                    if (error.message === "no twok found with given params") {
                        setLoading(false), setRefreshing(false)
                    } else {
                        alert("Si è verificato un errore durante il download dei twok dal server")
                    } }
                )
        }
    }

    //Ritorna e aggiunge un nuovo twok al buffer dopo lo scroll della lista dei twok
    //Casuale o da un certo twok in poi in base al tidSequence
    //L'UID può essere nullo
    //Se si verifica un errore nel download il twok non viene aggiungo alla lista dei twok
    function handleLoadMore() {
        if (tidSequence == -1) {
            TwokClass.dowloadOneTwok(globalVar.sid, uid, null)
            .then(result => twoks.push(result))
            .catch(error => alert("Si è verificato un errore durante il download di un twok dal server"))
        } else {
            TwokClass.dowloadOneTwok(globalVar.sid, null, tidSequence)
            .then(result => twoks.push(result), setTidSequence(tidSequence+1))
            .catch(error => alert("Si è verificato un errore durante il download di un twok dal server"))
        }
    
    }

    //Ritorna all'inizio della lista dei twok
    const ref = useRef(null);
    useScrollToTop(ref);

    //Effettua il refresh della lista dei twok
    function onRefresh() {
        showToks()
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
                    <FlatList style={styles.flatList}
                        data={twoks}
                        renderItem={(twok) => {  return (<TwokPost  data={twok} navigation={props.navigation} screen={props.screen} />) }}
                        snapToAlignment="start"
                        decelerationRate="fast"
                        onEndReached={handleLoadMore}
                        onEndReachedThreshold={0.7}
                        ListEmptyComponent={<View style={[styles.EmptytextHeader(headerHeight, tabBarHeight)]}><Text style={styles.EmptyMassage}>Nessun twok ancora pubblicato</Text></View>}
                        snapToInterval={Dimensions.get('window').height - headerHeight - tabBarHeight}
                        ref={ref}
                        refreshing={refreshing}
                        onRefresh={() => onRefresh()}
                    />
                </View>
            }
        </View>
    );

}

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

export default ListOfTwoks;