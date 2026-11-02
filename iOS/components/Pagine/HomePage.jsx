//IMPORT REACT UTILITIES
import { View, StatusBar } from 'react-native';
import React, { useContext } from 'react';
//utilities
import ListOfTwoks from '../Utilities/ListOfTwoks';
//context
import Context from '../../CONTEXT/ContextProvider'

//Pagina che mostra tutti i twok presenti nel server
//tidSequence -1 --> ordine casuale
//tidSequence  n --> dal tid specificato in poi
function HomePage(props) {

    //globalVar
    const { globalVar } = useContext(Context);

    return (
        <View>
            <StatusBar barStyle='dark-content'></StatusBar>
                {globalVar.network ?
                <ListOfTwoks
                    tidSequence={-1}
                    navigation={props.navigation}
                    screen={'Home_Screen'}
                >
                </ListOfTwoks>
                : undefined}
        </View>
    );
}

export default HomePage;