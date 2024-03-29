package gtlp.groundmc.lobby.inventory

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * Inventory for navigating the lobby
 */
internal object LobbyInventory {

    val TEMPLATE_INVENTORY: Inventory = Bukkit.createInventory(null, 6 * 9, "Lobby")

    fun create(inventoryHolder: InventoryHolder): Inventory {
        val clone = Bukkit.createInventory(inventoryHolder, TEMPLATE_INVENTORY.size, TEMPLATE_INVENTORY.title)
        clone.contents = TEMPLATE_INVENTORY.contents
        return clone
    }
}
