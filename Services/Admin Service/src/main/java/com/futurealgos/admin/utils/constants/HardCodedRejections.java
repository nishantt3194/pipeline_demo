package com.futurealgos.admin.utils.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HardCodedRejections {
    public static final String LAMINATE = "laminate";
    public static final String PAPER_REJECTION = "paperReject";
    public static final String SYRINGE = "syringe";

    private static final Map<String, List<Double>> twoML = new HashMap<>() {{
        put(LAMINATE, Arrays.asList(0.1618, 0.8));
        put(PAPER_REJECTION, Arrays.asList(0.2, 0.0405));
        put(SYRINGE, Arrays.asList(1.0, 0.7977));
    }};
    private static final Map<String, List<Double>> threeML = new HashMap<>() {{
        put(LAMINATE, Arrays.asList(0.161, 0.667));
        put(PAPER_REJECTION, Arrays.asList(0.333, 0.044));
        put(SYRINGE, Arrays.asList(1.0, 0.7951));
    }};
    private static final Map<String, List<Double>> threeMLIH = new HashMap<>() {{
        put(LAMINATE, Arrays.asList(0.161, 0.667));
        put(PAPER_REJECTION, Arrays.asList(0.333, 0.044));
        put(SYRINGE, Arrays.asList(1.0, 0.7951));
    }};
    private static final Map<String, List<Double>> fiveML = new HashMap<>() {{
        put(LAMINATE, Arrays.asList(0.145, 0.809));
        put(PAPER_REJECTION, Arrays.asList(0.191, 0.0405));
        put(SYRINGE, Arrays.asList(1.0, 0.8206));
    }};
    private static final Map<String, List<Double>> tenML = new HashMap<>() {{
        put(LAMINATE, Arrays.asList(0.108, 0.8113));
        put(PAPER_REJECTION, Arrays.asList(0.1887, 0.0251));
        put(SYRINGE, Arrays.asList(1.0, 0.8668));
    }};

    private HardCodedRejections() {
    }

    public static Map<String, List<Double>> rejectionConstants(String machine) {
        if (machine.equalsIgnoreCase("2ML FW A") || machine.equalsIgnoreCase("2ML FW B")) return twoML;
        if (machine.equalsIgnoreCase("3ML FW A") || machine.equalsIgnoreCase("3ML FW B")) return threeML;
        if (machine.equalsIgnoreCase("5ML FW A") || machine.equalsIgnoreCase("5ML FW B")) return fiveML;
        if (machine.equalsIgnoreCase("10ML FW A") || machine.equalsIgnoreCase("10ML FW B")) return tenML;
        if (machine.equalsIgnoreCase("3ML I") || machine.equalsIgnoreCase("3ML H"))
            return threeMLIH;

        throw new IllegalStateException("No HardCoded Rejection Values found for Machine " + machine);
    }
}
