package ca.ufv.comp380.assignment.a1.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import ca.ufv.comp380.assignment.a1.controller.AppController;
import ca.ufv.comp380.assignment.a1.model.ColorOption;
import ca.ufv.comp380.assignment.a1.model.CountryConfig;
import ca.ufv.comp380.assignment.a1.model.Province;

public class DfsBacktrackingAlgorithm implements CspAlgorithm {

    private AppController controller;
    private CountryConfig country;
    private List<ColorOption> availableColors;
    private Stack<Province> provinceStack = new Stack<>();
    private boolean isBacktracking = false;
    private Map<Province, Integer> provinceColorAttempt = new HashMap<>();

    @Override
    public void initialize(AppController controller, CountryConfig country, List<ColorOption> availableColors) {
        this.provinceStack = new Stack<>();
        this.controller = controller;
        this.country = country;
        this.availableColors = availableColors != null ? availableColors : ColorOption.getCanonicalColors();
        this.provinceColorAttempt = new HashMap<>();
        reset();
    }

    @Override
    public boolean step() {
        if (isFinished()) {
            return false;
        }
        if (this.isBacktracking) {
            return backtrack();
        } else {
            return assignNextColor();
        }
    }

    @Override
    public void reset() {
        this.isBacktracking = false;
        if (this.provinceColorAttempt != null) {
            this.provinceColorAttempt.clear();
        } else {
            this.provinceColorAttempt = new HashMap<>();
        }
        if (this.provinceStack != null) {
            this.provinceStack.clear();
        } else {
            this.provinceStack = new Stack<>();
        }
        if (this.country != null && !this.country.getProvinces().isEmpty()) {
            this.provinceStack.push(this.country.getProvinces().get(0));
        }
    }

    @Override
    public boolean isFinished() {
        return this.country == null || (this.controller != null
                && this.controller.getColorState().size() >= this.country.getProvinces().size());
    }

    @Override
    public boolean isSolved() {
        if (this.country == null || this.controller == null) {
            return false;
        }
        Map<Province, ColorOption> colorMap = this.controller.getColorState();
        if (colorMap.size() < this.country.getProvinces().size()) {
            return false;
        }
        for (Province p : this.country.getProvinces()) {
            ColorOption pColor = colorMap.get(p);
            if (pColor == null) {
                return false;
            }
            for (Province neighbor : p.getNeighbors()) {
                if (pColor.equals(colorMap.get(neighbor))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean assignNextColor() {
        if (this.provinceStack.isEmpty()) {
            Optional<Province> nextDisconnected = this.country.getProvinces().stream()
                    .filter(p -> controller.getProvinceColor(p) == null)
                    .findAny();
            if (nextDisconnected.isPresent()) {
                this.provinceStack.push(nextDisconnected.get());
                return true;
            }
            return false;
        }

        Province currentProvince = this.provinceStack.peek();
        ColorOption alreadyAssignedColor = this.controller.getProvinceColor(currentProvince);
        if (alreadyAssignedColor != null) {
            this.provinceStack.pop();
            if (this.provinceStack.isEmpty()) {
                Optional<Province> nextDisconnected = this.country.getProvinces().stream()
                        .filter(p -> controller.getProvinceColor(p) == null)
                        .findAny();
                if (nextDisconnected.isPresent()) {
                    this.provinceStack.push(nextDisconnected.get());
                    return true;
                }
                return false;
            }
            Province top = this.provinceStack.peek();
            Optional<Province> nextNeighborOpt = top.getAdjacentProvinces().stream()
                    .filter(p -> controller.getProvinceColor(p) == null)
                    .findAny();
            if (nextNeighborOpt.isPresent()) {
                this.provinceStack.push(nextNeighborOpt.get());
            }
            return true;
        }

        List<ColorOption> pickableColors = getPickableColors(currentProvince);

        if (pickableColors.isEmpty()) {
            this.isBacktracking = true;
            return true;
        }

        ColorOption chosenColor = pickableColors.get(0);
        this.provinceColorAttempt.put(currentProvince, 0);
        this.controller.updateColorState(currentProvince, chosenColor);

        return pushNextProvince(currentProvince);
    }

    private boolean backtrack() {
        if (this.provinceStack.isEmpty()) {
            return false;
        }
        this.provinceStack.pop();
        if (this.provinceStack.isEmpty()) {
            return false;
        }
        Province currentProvince = this.provinceStack.peek();
        this.controller.updateColorState(currentProvince, null);

        List<ColorOption> pickableColors = getPickableColors(currentProvince);
        int nextColorChoice = this.provinceColorAttempt.getOrDefault(currentProvince, 0) + 1;
        this.provinceColorAttempt.put(currentProvince, nextColorChoice);

        if (pickableColors.isEmpty()) {
            return true;
        } else if (pickableColors.size() == 1) {
            return true; // keep backtracking
        } else if (pickableColors.size() > nextColorChoice) {
            ColorOption nextChosenColor = pickableColors.get(nextColorChoice);
            this.controller.updateColorState(currentProvince, nextChosenColor);
            this.isBacktracking = false;
            return pushNextProvince(currentProvince);
        } else {
            return true; // keep backtracking
        }
    }

    private List<ColorOption> getPickableColors(Province province) {
        Set<ColorOption> neighbourColors = province.getNeighbors().stream()
                .map(n -> controller.getProvinceColor(n))
                .collect(Collectors.toSet());
        return availableColors.stream()
                .filter(ac -> !neighbourColors.contains(ac))
                .sorted()
                .toList();
    }

    private boolean pushNextProvince(Province currentProvince) {
        Optional<Province> nextProvinceOpt = currentProvince.getAdjacentProvinces().stream()
                .filter(p -> controller.getProvinceColor(p) == null)
                .findAny();

        if (nextProvinceOpt.isPresent()) {
            this.provinceStack.push(nextProvinceOpt.get());
            return true;
        }

        // Disconnected components logic
        Optional<Province> nextDisconnected = this.country.getProvinces().stream()
                .filter(p -> controller.getProvinceColor(p) == null)
                .findAny();

        if (nextDisconnected.isPresent()) {
            this.provinceStack.push(nextDisconnected.get());
            return true;
        }

        return false;
    }
}
