import { createContext, useState, useEffect } from "react";
import AsyncStorageClass from '../CLASS/AsyncStorageClass';
import NetInfo from "@react-native-community/netinfo";


const Context = createContext({});

export const ContextProvider = ({ children }) => {

    const [globalVar, setGlobalVar] = useState({
        sid: null,
        uid: null,
        network: null,
    });

    async function getInfouser() {
        await AsyncStorageClass.getInfo().then((user) => {
            setGlobalVar((globalVar) => ({ ...globalVar, sid: user.sid, uid: user.uid }))
        })
    }

    useEffect(() => {
        try {
            getInfouser()
        } catch (error) {
            console.log("Errore nel caricamento delle informazioni")
        }
    }, []);

    useEffect(() => {
        NetInfo.addEventListener(state => {
            setGlobalVar((globalVar) => ({ ...globalVar, network: state.isConnected }))    
        })
    }, []);

    return (
        <Context.Provider value={{ globalVar, setGlobalVar }}>
            {children}
        </Context.Provider>
    );
};

export default Context;
