package controller;

import model.adt.MyIHeap;
import model.state.PrgState;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {

    public static Map<Integer, IValue> conservativeGarbageCollector(List<Integer> symTableAddr, MyIHeap heap) {
        return null;
    }

    public static Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, MyIHeap heap) {
        return heap.getMap().entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, MyIHeap heap) {
        List<Integer> addresses = new ArrayList<>(symTableAddr);
        boolean newAddressesFound;
        do {
            newAddressesFound = false;
            List<Integer> newAddresses = getAddrFromSymTable(getReferencedValues(addresses, heap));

            for (Integer address : newAddresses) {
                if (!addresses.contains(address)) {
                    addresses.add(address);
                    newAddressesFound = true;
                }
            }
        } while (newAddressesFound);

        System.out.println("Collected addresses: " + addresses);

        Map<Integer, IValue> result = new HashMap<>();
        for (Map.Entry<Integer, IValue> entry : heap.getMap().entrySet()) {
            if (addresses.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }



    private static List<IValue> getReferencedValues(List<Integer> addresses, MyIHeap heap) {
        List<IValue> referencedValues = new ArrayList<>();
        for (Integer address : addresses) {
            IValue value = heap.getValue(address);
            if (value != null) {
                referencedValues.add(value);
            }
        }
        return referencedValues;
    }

    private static List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        List<Integer> addressList = new ArrayList<>();
        for (IValue value : symTableValues) {
            if (value instanceof RefValue) {
                addressList.add(((RefValue) value).getAddress());
            }
        }
        return addressList;
    }
}